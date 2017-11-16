package org.bvkatwijk.ochre.compiler;

import java.io.PrintWriter;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OchreCompiler {

	public byte[] compile(String source) {
		ClassWriter classWriter = getInternalWriter();
		TraceClassVisitor traceClassVisitor = getOperationWriter(classWriter);
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

		return classWriter.toByteArray();
	}

	private ClassWriter getInternalWriter() {
		return new ClassWriter(0);
	}

	private TraceClassVisitor getOperationWriter(ClassWriter classWriter) {
		return new TraceClassVisitor(
				classWriter,
				new PrintWriter(System.out));
	}

}
