package ca.applin.selmer.lang;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.applin.selmer.lang.ast.Ast;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

public class FileTest extends ParserTestBase {

    @Test
    public void testReadFromFile() throws Exception {
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        File file = new File(classLoader.getResource("test1.sel").getFile());
//        String code = Files.readString(file.toPath());
//        Ast ast = getAstFor(code, langContext);
//        assertNotNull(ast);
//        System.out.println(ast);
    }

}
