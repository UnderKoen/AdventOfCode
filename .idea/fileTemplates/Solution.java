#set($String = "abc")
#set($Int = 10)
#set($NAME = $String.format("Day%02d", $Int.parseInt($Day)))
#set($Year = $PACKAGE_NAME.split("year")[1])
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

#parse("File Header.java")
public class ${NAME} extends Solution {
    @Getter private final int day = ${Day};
    @Getter private final int year = $Year;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }
    
//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        //TODO write implementation
        
    }
}