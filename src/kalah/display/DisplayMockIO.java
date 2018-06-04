package kalah.display;

import com.qualitascorpus.testsupport.IO;

public class DisplayMockIO implements IGameInput, IGameOutput {
    IO io;

    public DisplayMockIO(IO io) {
        this.io = io;
    }

    @Override
    public String readLine(String prompt) {
        return io.readFromKeyboard(prompt);
    }

    @Override
    public void writeLine(String line) {
        io.println(line);
    }
}
