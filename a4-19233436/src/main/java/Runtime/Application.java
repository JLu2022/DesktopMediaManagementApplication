package Runtime;

import Controller.Controller;
import Model.Model;
import View.*;

import java.io.IOException;

/**
 * @author: Junlin Lu
 * @date: 16/04/2020
 * @version: 1.0.0
 * @description: Runtime application class
 */
public class Application {
    public static void main(String[] args) throws IOException {
        Model model = Model.getModel();
        model.init("data.json");
        View view = new View();
        Controller controller = new Controller(model,view);

    }
}
