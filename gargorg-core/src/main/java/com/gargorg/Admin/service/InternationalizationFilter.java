package com.gargorg.Admin.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.LocaleResolver;

public class InternationalizationFilter extends GenericFilterBean 
 {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InternationalizationFilter.class);
	private String localeParam = "locale";
	private LocaleResolver localeResolver;

	public InternationalizationFilter(String localeParam,
			LocaleResolver localeResolver) {
		this.localeParam = localeParam;
		this.localeResolver = localeResolver;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req,
			ServletResponse res, final FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		if (localeResolver == null) {
			throw new IllegalStateException(
					"No LocaleResolver found: not in a DispatcherServlet request?");
		} else {
			final String newLocale = request.getParameter(localeParam);
			if (newLocale != null) {
				final Locale locale = StringUtils.parseLocaleString(newLocale
						.toLowerCase());
				LocaleContextHolder.setLocale(locale);
				LocaleEditor localeEditor = new LocaleEditor();
				localeEditor.setAsText(newLocale);
				localeResolver.setLocale(request, response,
						(Locale) localeEditor.getValue());
				LOGGER.debug("change locale to " + locale.getLanguage() + "_"
						+ locale.getCountry() + " at Thread"
						+ Thread.currentThread().getId());
			}
			try {
				filterChain.doFilter(request, response);
			} finally {
				//LocaleContextHolder.resetLocaleContext();
			}
		}
	}

}