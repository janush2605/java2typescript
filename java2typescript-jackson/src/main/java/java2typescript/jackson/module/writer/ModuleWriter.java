package java2typescript.jackson.module.writer;

import java2typescript.jackson.module.grammar.Module;

import java.io.IOException;
import java.io.Writer;

public interface ModuleWriter {
    void write(Module module, Writer writer) throws IOException;
}
