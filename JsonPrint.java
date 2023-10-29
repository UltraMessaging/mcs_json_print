import java.io.*;
import java.util.*;

import org.apache.logging.log4j.Logger;

import com.latencybusters.lbm.UMMonDB;
import com.latencybusters.lbm.UMSMonProtos.UMSMonMsg;
import com.latencybusters.lbm.UMPMonProtos.UMPMonMsg;
import com.latencybusters.lbm.DROMonProtos.DROMonMsg;
import com.latencybusters.lbm.SRSMonProtos.SRSMonMsg;

import com.google.protobuf.Message;
import com.google.protobuf.util.*;

/*
  Copyright (c) 2023-2023 Informatica Corporation
  Permission is granted to licensees to use or alter this software for any
  purpose, including commercial applications, according to the terms laid
  out in the Software License Agreement.

  This source code example is provided by Informatica for educational
  and evaluation purposes only.

  THE SOFTWARE IS PROVIDED "AS IS" AND INFORMATICA DISCLAIMS ALL WARRANTIES 
  EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF 
  NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR 
  PURPOSE.  INFORMATICA DOES NOT WARRANT THAT USE OF THE SOFTWARE WILL BE 
  UNINTERRUPTED OR ERROR-FREE.  INFORMATICA SHALL NOT, UNDER ANY CIRCUMSTANCES,
  BE LIABLE TO LICENSEE FOR LOST PROFITS, CONSEQUENTIAL, INCIDENTAL, SPECIAL OR 
  INDIRECT DAMAGES ARISING OUT OF OR RELATED TO THIS AGREEMENT OR THE 
  TRANSACTIONS CONTEMPLATED HEREUNDER, EVEN IF INFORMATICA HAS BEEN APPRISED OF 
  THE LIKELIHOOD OF SUCH DAMAGES.
  All of the documentation and software included in this and any
  other Informatica Inc. Ultra Messaging Releases
  Copyright (C) Informatica Inc. All rights reserved.
  
  Redistribution and use in source and binary forms, with or without
  modification, are permitted only as covered by the terms of a
  valid software license agreement with Informatica Inc.
*/


public class JsonPrint implements UMMonDB
{
	// Make these static because only one instance is needed and
	// we need the shutdown hook to be able to get to them.
	private Properties _properties = null;
	private Logger _logger = null;
	private String _outFilePath = null;
	private BufferedWriter _outFileWriter = null;
	private Thread _cleanup = null;


	public JsonPrint() {
		// Just make sure we don't get multiple instances.
		if (_outFileWriter != null) {
			try {
				System.err.println("JsonPrint: Double create?");
			} catch (Exception ignore) { }
		}
	}

	public void setProperties(Properties properties) {
		_properties = properties;
	}

	public void setLogger(Logger logger) {
		_logger = logger;
	}

	public void connect() throws IOException {
		// Open output file, default to "-" which means standard out.
		_outFilePath = _properties.getProperty("outFilePath", "-");
		try {
			if (_outFilePath.compareTo("-") == 0) {
				_outFileWriter = new BufferedWriter(new OutputStreamWriter(System.out));
			} else {
				_outFileWriter = new BufferedWriter(new FileWriter(_outFilePath));
			}
		} catch (Exception e) {
			_logger.error("MCS-999000-1: jsonprint: Error opening " + _outFilePath + ": " + e.getMessage());
			_outFileWriter = null;
			throw new IOException("Error opening " + _outFilePath + ": " + e.getMessage());
		}

		/* Set up shutdown hook to clean up. */
		try {
			_cleanup = new Thread(() -> {
				try {
					_outFileWriter.flush();
					_outFileWriter.close();
					_outFileWriter = null;
				} catch (Exception e) { System.out.println("Exception: " + e.getMessage()); }
			} );
			Runtime.getRuntime().addShutdownHook(_cleanup);
		} catch (Exception e) {
			_logger.error("MCS-999000-3: jsonprint: Error adding shutdown hook: " + e.getMessage());
			throw new IOException("Error adding shutdown hook: " + e.getMessage());
		}

		_logger.info("MCS-999000-4: jsonprint started, outFilePath=" + _outFilePath);
	}

	public void write(UMSMonMsg umsMonMsg) throws IOException {
		printMsg(umsMonMsg);
	}

	public void write(DROMonMsg droMonMsg) throws IOException {
		printMsg(droMonMsg);
	}

	public void write(UMPMonMsg umpMonMsg) throws IOException {
		printMsg(umpMonMsg);
	}

    public void write(SRSMonMsg srsMonMsg) throws IOException {
		printMsg(srsMonMsg);
    }

	private void printMsg(Message msg) throws IOException {
		String json = null;
		try {
            json = JsonFormat.printer().includingDefaultValueFields().print(msg);
        } catch (Exception e) {
			_logger.error("MCS-999000-5: jsonprint: Error formatting json: " + e.getMessage());
			throw new IOException("Error formatting json: " + e.getMessage());
        }

        if (_outFileWriter != null) {
			try {
        		_outFileWriter.write(json);
        	} catch (Exception e) {
				_logger.error("MCS-999000-6: jsonprint: Error writing to " + _outFilePath + ": " + e.getMessage());
				_outFileWriter = null;
				throw new IOException("Error writing to " + _outFilePath + ": " + e.getMessage());
        	}
        }
	}

	public void disconnect() throws IOException {
		if (_outFileWriter != null) {
			try {
				_outFileWriter.flush();
				_outFileWriter.close();
				_outFileWriter = null;
        	} catch (Exception e) {
				_logger.error("MCS-999000-7: jsonprint: Error closing " + _outFilePath + ": " + e.getMessage());
				_outFileWriter = null;
				throw new IOException("Error closing " + _outFilePath + ": " + e.getMessage());
        	}
		} else {
			_logger.error("MCS-999001-9: Double disconnect?");
		}
	}
}
