/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.emf.search.impl;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.lucene.analysis.Analyzer;
import org.gecko.emf.search.document.EObjectDocumentIndexObjectContext;
import org.gecko.search.api.IndexListener;
import org.gecko.search.document.LuceneIndexService;
import org.gecko.search.document.index.LucenePushStreamIndexImpl;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.util.pushstream.PushEvent;
import org.osgi.util.pushstream.PushStreamProvider;
import org.osgi.util.pushstream.QueuePolicyOption;
import org.osgi.util.pushstream.SimplePushEventSource;

/**
 * EMF implementation of the {@link LucenePushStreamIndexImpl}
 * @author Mark Hoffmann
 * @since 08.03.2023
 */
@Component(name = "EMFLuceneIndex", service = LuceneIndexService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class EObjectLuceneIndex extends LucenePushStreamIndexImpl<EObjectDocumentIndexObjectContext> {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.impl.LuceneIndexImpl#activate(org.gecko.search.document.impl.LuceneIndexImpl.Config, org.osgi.service.component.ComponentContext)
	 */
	@Override
	@Activate
	protected void activate(Config serviceConfig, BundleContext context) throws ConfigurationException {
		super.activate(serviceConfig, context);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.impl.LuceneIndexImpl#deactivate()
	 */
	@Override
	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.impl.LuceneIndexImpl#setAnalyzer(org.apache.lucene.analysis.Analyzer)
	 */
	@Override
	@Reference(name="analyzer", target="(type=standard)")
	public void setAnalyzer(Analyzer analyzer) {
		super.setAnalyzer(analyzer);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.LuceneIndexImpl#addIndexListener(org.gecko.search.api.IndexListener)
	 */
	@Override
	@Reference(name = "indexListener", cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
	protected void addIndexListener(IndexListener listener) {
		super.addIndexListener(listener);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.LuceneIndexImpl#removeIndexListener(org.gecko.search.api.IndexListener)
	 */
	@Override
	protected void removeIndexListener(IndexListener listener) {
		super.removeIndexListener(listener);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.search.document.impl.LuceneIndexImpl#createSimplePushEventSource()
	 */
	@Override
	protected SimplePushEventSource<EObjectDocumentIndexObjectContext> createSimplePushEventSource() {
		PushStreamProvider psp = getPushStreamProvider();
		return psp.buildSimpleEventSource(EObjectDocumentIndexObjectContext.class).withBuffer(new ArrayBlockingQueue<PushEvent<? extends EObjectDocumentIndexObjectContext>>(100)).withQueuePolicy(QueuePolicyOption.BLOCK).build();
	}

}
