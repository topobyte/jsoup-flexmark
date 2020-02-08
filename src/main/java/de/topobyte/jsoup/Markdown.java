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

package de.topobyte.jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import de.topobyte.jsoup.flexmark.CustomExtension;
import de.topobyte.melon.commons.io.Resources;

public class Markdown
{

	private static MutableDataSet options = new MutableDataSet();

	static {
		Extension tables = new TablesExtension() {

			@Override
			public void rendererOptions(MutableDataHolder options)
			{
				options.set(TablesExtension.CLASS_NAME, "table");
			}

		};

		Extension strikethrough = StrikethroughExtension.create();
		Extension wikilink = WikiLinkExtension.create();
		Extension custom = CustomExtension.create();

		options.set(Parser.EXTENSIONS,
				Arrays.asList(tables, strikethrough, custom, wikilink));
	}

	private static Parser parser()
	{
		return Parser.builder(options).build();
	}

	private static HtmlRenderer renderer()
	{
		return HtmlRenderer.builder(options).build();
	}

	public static void generate(Element container, String markdown)
	{
		Document document = parser().parse(markdown);
		String html = renderer().render(document);
		container.append(html);
	}

	public static void renderResource(Element content, String resource)
			throws IOException
	{
		String markdown = Resources.loadString(resource);
		generate(content, markdown);
	}

	public static void renderFile(Element content, Path file) throws IOException
	{
		InputStream input = Files.newInputStream(file);
		String markdown = IOUtils.toString(input, StandardCharsets.UTF_8);

		generate(content, markdown);
	}

}
