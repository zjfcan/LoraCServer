package com.guina.loratracker.util;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.logging.Logger;

import com.guina.loratracker.model.LoraCommand;
import com.guina.loratracker.model.LoraCommandType;

public class LoraUtil
{
	private static Logger logger = Logger.getLogger(LoraUtil.class);

	public static byte[] getCommandData(LoraCommand command)
	{
		int length = command.getData().length();
		logger.info("Login info length: " + length);
		byte[] loginInfoBuff = new byte[length + 8];
		loginInfoBuff[0] = (byte) 0xB0;
		loginInfoBuff[1] = (byte) 0xB0;
		loginInfoBuff[2] = (byte) 0xB0;
		loginInfoBuff[3] = (byte) 0xB0;

		// command
		LoraCommandType com = command.getCommandType();
		if (LoraCommandType.HEART_BEAT.equals(com))
		{
			loginInfoBuff[4] = (byte) 0xE0;
		}
		else if (LoraCommandType.JASON_OBJ.equals(com))
		{
			loginInfoBuff[4] = (byte) 0xE1;
		}
		else
		{
			// unexpected command
			throw new RuntimeException("Unexpected command: " + com);
		}

		// Length, Little endian
		loginInfoBuff[5] = (byte) (length & 0xFF);
		loginInfoBuff[6] = (byte) (length >> 8 & 0xFF);

		byte[] loginInfoArr = command.getData().getBytes();
		for (int idx = 0; idx < length; idx++)
		{
			loginInfoBuff[idx + 7] = loginInfoArr[idx];
		}
		loginInfoBuff[length + 7] = (byte) 0xF0;// Ending

		return loginInfoBuff;
	}

	public static String bytesToHex(byte[] in)
	{
		final StringBuilder builder = new StringBuilder();
		for (byte b : in)
		{
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	public static LoraCommand readData(InputStream inputStream) throws IOException
	{
		LoraCommand commandReceived = new LoraCommand();
		byte[] buff = new byte[1024];
		while (true)
		{
			if (inputStream.available() < 7)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					// do nothing
					logger.debug("Sleep interupted.", e);
				}
				continue;
			}
			// find start id
			if (inputStream.read(buff, 0, 7) < 0)
			{
				continue;
			}

			for (int idx = 0; idx < 4; idx++)
			{
				if (!(buff[0] == (byte) 0xB0))
				{
					logger.error("unexpected start ID: " + String.valueOf(buff));
					continue;
				}
			}

			// command
			// data of heart beat
			if (buff[4] == (byte) 0xE0)
			{
				commandReceived.setCommandType(LoraCommandType.HEART_BEAT);
			}
			// JSON object
			else if (buff[4] == (byte) 0xE1)
			{
				commandReceived.setCommandType(LoraCommandType.JASON_OBJ);
			}
			else
			{
				// unexpected command
				logger.error("Unexpected command: " + String.valueOf((byte) buff[4]));
				continue;
			}
			logger.info("Received command type: " + commandReceived.getCommandType());

			// Little Endian
			int payloadLen = ((buff[6] & 0xff) << 8) | (buff[5] & 0xff);
			logger.info("Received data length: " + payloadLen);
			// Pay load data
			if (inputStream.read(buff, 7, payloadLen) < 0)
			{
				logger.error("No data found: " + String.valueOf(buff));
				continue;
			}

			String data = "";
			for (int idx = 7; idx < payloadLen + 7; idx++)
			{
				data += String.valueOf((char) buff[idx]);
			}
			//ManThink is using Class as attribute, but it is a Java keyword
			commandReceived.setData(data.replaceAll("Class", "loraClass"));

			inputStream.read(buff, 7 + payloadLen, 1);
			if (buff[7 + payloadLen] != (byte) 0xF0)
			{
				logger.error("unexpected end of data: " + buff[7 + payloadLen]);
			}
			break;
		}
		logger.info("Received command: " + commandReceived);

		return commandReceived;
	}
}
