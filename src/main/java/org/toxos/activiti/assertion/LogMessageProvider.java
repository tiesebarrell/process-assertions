/*******************************************************************************
 * Copyright 2013 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.toxos.activiti.assertion;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provider for messages corresponding with {@link LogMessage}s for a specific
 * {@link Locale}. The provider can optionally be immutable, meaning the initial
 * Locale will not be changed to adhere to changes to the default Locale after
 * instantiation.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageProvider {

	private final String baseName;

	private Locale locale;

	private ResourceBundle bundle;

	private boolean immutable = false;

	/**
	 * Constructs a mutable LogMessageProvider for the default Locale.
	 */
	public LogMessageProvider() {
		super();
		this.baseName = Constants.LOG_MESSAGES_BUNDLE_NAME;
		this.immutable = false;
		this.locale = Locale.getDefault();
	}

	/**
	 * Constructs an immutable LogMessageProvider for the provided Locale.
	 * 
	 * @param locale
	 *            the Locale to be used
	 */
	protected LogMessageProvider(final Locale locale) {
		super();
		this.baseName = Constants.LOG_MESSAGES_BUNDLE_NAME;
		this.immutable = true;
		this.locale = locale;
	}

	/**
	 * Constructs an immutable LogMessageProvider that uses the provided
	 * baseName for the provided Locale.
	 * 
	 * @param baseName
	 *            the baseName for bundle resolution
	 * @param locale
	 *            the Locale to be used
	 */
	protected LogMessageProvider(final String baseName, final Locale locale) {
		super();
		this.baseName = baseName;
		this.immutable = true;
		this.locale = locale;
	}

	/**
	 * Constructs an mutable LogMessageProvider that uses the provided baseName
	 * for the default Locale.
	 * 
	 * @param baseName
	 *            the baseName for bundle resolution
	 * @param locale
	 *            the Locale to be used
	 */
	protected LogMessageProvider(final String baseName) {
		super();
		this.baseName = baseName;
		this.immutable = false;
		this.locale = Locale.getDefault();
	}

	public String getMessageByKey(final String key) {
		return getBundle().getString(key);
	}

	private ResourceBundle getBundle() {
		loadBundleIfRequired();
		return bundle;
	}

	private void loadBundleIfRequired() {
		if (initialBundleLoadIsRequired()) {
			loadBundle();
		} else if (bundleReloadIsRequired()) {
			this.locale = Locale.getDefault();
			loadBundle();
		}
	}

	private boolean initialBundleLoadIsRequired() {
		return bundle == null;
	}

	private boolean bundleReloadIsRequired() {
		return immutable == false && !bundle.getLocale().equals(Locale.getDefault());
	}

	private void loadBundle() {
		bundle = ResourceBundle.getBundle(baseName, this.locale);
	}

}
