package com.Ian.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.pojo.Game;
import com.Ian.pojo.GameRound;

/**
 * 
 * MySQL数据库操作类(猜数字游戏）
 * 
 * @author ian
 * @date 2016-07-06
 *
 */
public class GameDBUtil {

	private static Logger log = LoggerFactory.getLogger(GameDBUtil.class);
	
	/**
	 * 
	 * 保存游戏信息
	 * 
	 * @param req
	 * @param game
	 * @return
	 */
	public static int saveGame(HttpServletRequest req, Game game){
		int gameId = -1;
		String sql = "insert into game(open_id, game_answer, create_time, game_status, finish_time) values(?, ?, ?, ?, ?)";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, game.getOpenId());
			ps.setString(2, game.getGameAnswer());
			ps.setString(3, game.getCreateTime());
			ps.setInt(4, game.getGameStatus());
			ps.setString(5, game.getFinishTime());
			ps.executeUpdate();
			ps.close();
			sql = "select game_id from game where open_id = ? and game_answer = ? order by game_id desc limit 0,1";
			ps = conn.prepareStatement(sql);
			ps.setString(1, game.getOpenId());
			ps.setString(2, game.getGameAnswer());
			rs = ps.executeQuery();
			if (rs.next()){
				gameId = rs.getInt("game_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insert game failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return gameId;
	}
	
	/**
	 * 
	 * 获取用户最近一次创建的游戏
	 * 
	 * @param req
	 * @param openId
	 * @return
	 */
	public static Game getLastGame(HttpServletRequest req, String openId){
		Game game = null;
		String sql = "select * from game where open_id = ? order by game_id desc limit 0,1";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			rs = ps.executeQuery();
			if (rs.next()){
				game = new Game();
				game.setGameId(rs.getInt("game_id"));
				game.setOpenId(rs.getString("open_id"));
				game.setGameAnswer(rs.getString("game_answer"));
				game.setCreateTime(rs.getString("create_time"));
				game.setGameStatus(rs.getInt("game_status"));
				game.setFinishTime(rs.getString("finish_time"));
			}
		} catch (SQLException e) {
			game = null;
			e.printStackTrace();
			log.error("select game failed");
		} finally{
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return game;
	}
	
	/**
	 * 
	 * 根据游戏id修改游戏状态和完成时间
	 * 
	 * @param req
	 * @param gameId
	 * @param gameStatus
	 * @param finishTime
	 */
	public static void updateGame(HttpServletRequest req, int gameId, int gameStatus, String finishTime){
		String sql = "update game set game_status = ?, finish_time = ? where game_id = ?";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameStatus);
			ps.setString(2, finishTime);
			ps.setInt(3, gameId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update game failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
	}
	
	/**
	 * 
	 * 保存游戏的回合信息
	 * 
	 * @param req
	 * @param gameRound
	 */
	public static void saveGameRound(HttpServletRequest req, GameRound gameRound){
		String sql = "insert into game_round(game_id, open_id, guess_number, guess_time, guess_result) values(?, ?, ?, ?, ?)";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameRound.getGameId());
			ps.setString(2, gameRound.getOpenId());
			ps.setString(3, gameRound.getGuessNumber());
			ps.setString(4, gameRound.getGuessTime());
			ps.setString(5, gameRound.getGuessResult());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insert game_round failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
	}
	
	/**
	 * 
	 * 根据游戏id获取游戏的全部回合
	 * 
	 * @param req
	 * @param gameId
	 * @return
	 */
	public static List<GameRound> findAllRoundByGameId(HttpServletRequest req, int gameId){
		List<GameRound> roundList = new ArrayList<GameRound>();
		String sql = "select * from game_round where game_id = ? order by id asc";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gameId);
			rs = ps.executeQuery();
			GameRound round = null;
			while (rs.next()){
				round = new GameRound();
				round.setGameId(rs.getInt("game_id"));
				round.setOpenId(rs.getString("open_id"));
				round.setGuessNumber(rs.getString("guess_number"));
				round.setGuessTime(rs.getString("guess_time"));
				round.setGuessResult(rs.getString("guess_result"));
				roundList.add(round);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("select game_round failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return roundList;
	}
	
	public static HashMap<Integer, Integer> getScoreByOpenId(HttpServletRequest req, String openId){
		HashMap<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
		String sql = "select game_status, count(*) from game where open_id = ? group by game_status order by game_status asc";
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			rs = ps.executeQuery();
			while (rs.next()){
				scoreMap.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("select game for result failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return scoreMap;
	}
}
