package org.utwente.cs.ds.semi.lod.ieee.scraping.exception;

public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/** HTTP Status code returned from the API*/
	private int errorCode;
	
	public ApplicationException() {
		super();
	}

	public ApplicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApplicationException(String arg0) {
		super(arg0);
	}

	public ApplicationException(int errorCode, String errorResponse) {
		super(errorResponse);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
