package org.bvkatwijk.ochre.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.parboiled.Action;
import org.parboiled.Context;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

public class Actions {

	public Action<String> storeIdentifier(StringVar identifier) {
		return new Action<String>() {

			@Override
			public boolean run(Context<String> context) {
				identifier.set(context.getMatch().trim());
				return false;
			}
		};
	}

	public Action<String> registerImportSubblock(StringVar parent, Var<List<String>> children) {
		return new Action<String>() {

			@Override
			public boolean run(Context<String> context) {
				children.get().add(parent.get() + "." + context.getMatch());
				return false;
			}
		};
	}

	public Action<String> collapseChildren(Var<List<String>> imports, StringVar identifier,
			Var<List<String>> children) {
		return new Action<String>() {

			@Override
			public boolean run(Context<String> context) {
				if (children.get().isEmpty()) {
					imports
							.get()
							.add(identifier.get());
				} else {
					imports
							.get()
							.addAll(children.get());
				}
				return true;
			}
		};
	}

	public Action<String> clear(Var<List<String>> children) {
		return new Action<String>() {

			@Override
			public boolean run(Context<String> context) {
				children.get().clear();
				return true;
			}
		};
	}

	public Action<String> registerImports(Var<List<String>> imports) {
		return new Action<String>() {

			@Override
			public boolean run(Context<String> context) {
				context.getValueStack().push(imports
						.get()
						.stream()
						.map(String::trim)
						.map(it -> "import " + it + ";")
						.collect(Collectors.joining("\n")));
				return true;
			}
		};

	}

}
