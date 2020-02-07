// Copyright 2020 Sebastian Kuerten
//
// This file is part of jsoup-flexmark.
//
// jsoup-flexmark is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jsoup-flexmark is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jsoup-flexmark. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.jsoup.flexmark;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public class CustomExtension implements HtmlRenderer.HtmlRendererExtension
{

	@Override
	public void rendererOptions(final MutableDataHolder options)
	{
		// empty
	}

	@Override
	public void extend(final HtmlRenderer.Builder rendererBuilder,
			final String rendererType)
	{
		rendererBuilder.linkResolverFactory(new CustomLinkResolver.Factory());
		rendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
	}

	public static CustomExtension create()
	{
		return new CustomExtension();
	}

}