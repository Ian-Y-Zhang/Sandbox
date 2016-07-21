package com.Ian.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.Ian.message.resp.Article;
import com.Ian.message.resp.ImageMessage;
import com.Ian.message.resp.MusicMessage;
import com.Ian.message.resp.NewsMessage;
import com.Ian.message.resp.TextMessage;
import com.Ian.message.resp.VideoMessage;
import com.Ian.message.resp.VoiceMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * 消息处理工具类
 * 
 * @author Ian
 * @date 2016-05-13
 */

public class MessageUtil {
	// 请求消息类型
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	// 事件类型
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_TYPE_SCAN = "scan";
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 响应消息类型
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	// 不需要CDATA的XML结点列表
	private static Set<String> ss = new HashSet<String>();
	{
		ss.add("CreateTime");
		ss.add("ArticleCount");
		ss.add("Latitude");
		ss.add("Longitude");
		ss.add("Precision");
	}
	
	/**
	 * 
	 * 解析微信发来的请求(XML)
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;
		return map;
	}

	/**
	 * 
	 * 扩展xstream使其支持CDATA
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
					if (ss.contains(name)) {
						cdata = false;
					} else {
						cdata = true;
					}
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 
	 * 文本消息对象转换成XML
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 
	 * 图片消息对象转换成XML
	 * 
	 * @param imageMessage 图片消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 
	 * 语音消息对象转换成XML
	 * 
	 * @param voiceMessage 语音消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 
	 * 视频消息对象转换成XML
	 * 
	 * @param videoMessage 视频消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 
	 * 音乐消息对象转换成XML
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 
	 * 图文消息对象转换成XML
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 * 
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
