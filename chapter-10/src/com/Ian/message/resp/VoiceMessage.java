package com.Ian.message.resp;

/**
 * 
 * 语音消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class VoiceMessage extends BaseMessage {

	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		this.Voice = voice;
	}

}
