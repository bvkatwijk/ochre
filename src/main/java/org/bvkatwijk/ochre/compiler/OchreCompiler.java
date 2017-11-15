package org.bvkatwijk.ochre.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

//package org.bvkatwijk.ochre.compiler;
//
//public class Example {
//
//	private final String name;
//
//	public Example(String name) {
//		this.name = name;
//	}
//
//}

public class OchreCompiler {

	public OchreCompiler(InputStream inputStream) {

		ClassWriter classWriter = new ClassWriter(0);
		TraceClassVisitor traceClassVisitor = new TraceClassVisitor(
				classWriter,
				new PrintWriter(System.out));
		traceClassVisitor.visit(
				Opcodes.V1_8,
				Opcodes.ACC_PUBLIC,
				"org/bvkatwijk/ochre/compiler/Example",
				null,
				"java/lang/Object",
				null);
		traceClassVisitor.visitField(
				Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL,
				"name",
				Type.getType(String.class).getDescriptor(),
				null,
				null);
		traceClassVisitor.visitEnd();

		writeToFile(classWriter.toByteArray());
	}

	private void writeToFile(byte[] b) {
		try {
			FileUtils.writeByteArrayToFile(new File("build/Example.class"), b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static byte[] fileToByteArray(InputStream inputStream) {
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
