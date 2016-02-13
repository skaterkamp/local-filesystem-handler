/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.streichelzoo;

import java.util.Iterator;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Stub for FacesContext mock.
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class ContextMocker extends FacesContext {

	private ContextMocker() {
	}

	private static final Release RELEASE = new Release();

	@Override
	public Application getApplication() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public ExternalContext getExternalContext() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public FacesMessage.Severity getMaximumSeverity() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public RenderKit getRenderKit() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public boolean getRenderResponse() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public boolean getResponseComplete() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public ResponseStream getResponseStream() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public ResponseWriter getResponseWriter() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public UIViewRoot getViewRoot() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void setViewRoot(UIViewRoot root) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void release() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void renderResponse() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	@Override
	public void responseComplete() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}

	private static class Release implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			setCurrentInstance(null);
			return null;
		}
	}

	public static FacesContext mockFacesContext() {
		FacesContext context = Mockito.mock(FacesContext.class);
		setCurrentInstance(context);
		Mockito.doAnswer(RELEASE)
				.when(context)
				.release();
		return context;
	}

}
