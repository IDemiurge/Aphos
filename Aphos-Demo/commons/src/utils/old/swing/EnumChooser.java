package utils.old.swing;

public class EnumChooser  {

    public <T> T  choose(Class<T> clazz) {
        int i = DialogMaster.optionChoice(clazz.getEnumConstants(), "Choose "
         + clazz.getSimpleName() + " constant");
        if (i == -1) {
            return null;
        }
        return clazz.getEnumConstants()[i];
    }

}
