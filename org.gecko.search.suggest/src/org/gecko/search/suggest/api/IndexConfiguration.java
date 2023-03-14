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
package org.gecko.search.suggest.api;

import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Lucene Index configuration
 * @author Mark Hoffmann
 * @since 14.03.2023
 */
@ObjectClassDefinition
public @interface IndexConfiguration {
	String id();
	String directory_type();
	String base_path() default "";
	int batchSize() default 500;
	long windowSize() default 500;
	int indexThreads() default 4;
}
