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

import java.util.Set;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public class CustomLinkResolver implements LinkResolver
{

	static class Factory implements LinkResolverFactory
	{
		@Override
		public Set<Class<? extends LinkResolverFactory>> getAfterDependents()
		{
			return null;
		}

		@Override
		public Set<Class<? extends LinkResolverFactory>> getBeforeDependents()
		{
			return null;
		}

		@Override
		public boolean affectsGlobalScope()
		{
			return false;
		}

		@Override
		public LinkResolver create(final LinkResolverContext context)
		{
			return new CustomLinkResolver(context);
		}
	}

	public CustomLinkResolver(final LinkResolverContext context)
	{
		// empty
	}

	@Override
	public ResolvedLink resolveLink(final Node node,
			final LinkResolverContext context, final ResolvedLink link)
	{
		if (node instanceof WikiLink) {
			String def = link.getUrl();
			GithubLink githubLink = new GithubLink(def);
			if (githubLink.isValid()) {
				return link.withStatus(LinkStatus.VALID)
						.withUrl(githubLink.url());
			}
			return link.withStatus(LinkStatus.VALID).withUrl(def);
		}
		return link;
	}

}