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

import java.util.List;

import com.google.common.base.Splitter;

public class GithubLink
{

	private String def;
	private boolean valid = false;

	private String user = null;
	private String repo = null;
	private String path = null;

	public GithubLink(String def)
	{
		this.def = def;
		valid = def.startsWith("@github(") && def.endsWith(")");
		if (!valid) {
			return;
		}

		String bracketed = def.substring(8, def.length() - 1);
		List<String> parts = Splitter.on(",").trimResults()
				.splitToList(bracketed);
		user = parts.get(0);
		if (parts.size() > 1) {
			repo = parts.get(1);
		}
		if (parts.size() > 2) {
			path = parts.get(2);
		}
	}

	public String getDef()
	{
		return def;
	}

	public boolean isValid()
	{
		return valid;
	}

	public boolean hasRepo()
	{
		return repo != null;
	}

	public boolean hasPath()
	{
		return path != null;
	}

	public String getUser()
	{
		return user;
	}

	public String getRepo()
	{
		return repo;
	}

	public String getPath()
	{
		return path;
	}

	public String text()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(user);
		if (hasRepo()) {
			builder.append("/");
			builder.append(repo);
		}
		if (hasPath()) {
			builder.append(path);
		}
		return builder.toString();
	}

	public String url()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("https://www.github.com/");
		builder.append(user);
		if (hasRepo()) {
			builder.append("/");
			builder.append(repo);
		}
		if (hasPath()) {
			builder.append(path);
		}
		return builder.toString();
	}

}
