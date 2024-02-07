package apps.utils.prompt;

import apps.utils.prompt.data.PromptDataManager;
import apps.utils.prompt.enums.PromptEnums;
import apps.utils.prompt.token.TokenMixer;
import module.campaign.data.enums.AssetEnums;
import utils.old.ContainerUtils;
import utils.old.StringMaster;
import utils.old.swing.DialogMaster;
import utils.old.swing.EnumChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Alexander on 9/2/2023
 * <p>
 * on hotkey, ... F[x] for prompt type of x? constructor via Joption sequence
 * <p>
 * i: AHK => hotkey + ctrl enter
 * <p>
 * GPT will have SEPARATE UTIL?
 * <p>
 * Wanna use it ESPECIALLY on the MAC! Hotkeys for mac? Or some other way? Button panel - maybe Data persistence - just
 * append to some yaml file? What we'll save: successful prompt remixes w/ fixed input text (which can then be further
 * mixed or used AS IS )
 */
/*

 */
public class SdPromptGen {
    public static void main(String[] args) {

        PromptDataManager.read();
        int i = JOptionPane.showConfirmDialog(null, "Remix prompt?");
        if (i == JOptionPane.OK_OPTION) {
            while (true){
            String prompt = JOptionPane.showInputDialog("Prompt");
            java.util.List<String> strings = new ArrayList<>(Arrays.asList(prompt.split(";")));
            Collections.shuffle(strings);
            prompt = ContainerUtils.join("; ", strings);
            StringSelection selection = new StringSelection(prompt);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            if (!DialogMaster.confirm("Continue?"))
                return;
            }
        }

        PromptEnums.PromptStyle style = null;
        PromptEnums.PromptType type = PromptEnums.PromptType.Event_Pic;
        String input = "radiant";
        String arg = ""; //more info for subtype?
        Object subType = AssetEnums.AphosEventType.fascination;
        PromptModel promptModel = new PromptModel(style, type, subType, input);

        TokenMixer.PromptTemplate template = new EnumChooser().choose(TokenMixer.PromptTemplate.class);
        while (true) {


            String build =
                    "close-up shot, cinematic character portrait, mystery Antagonist, Climax, " +
                            new PromptBuilder().build(promptModel, template);

            build += " " +
                    " epic view, low angle, scene illustration dramatic lighting " +
                    // "Foreshadowing, " +
                    // "Denouement, " +
                    // ", " +
                    // "Climax, " +
                    // "Flashback, " +
                    "sharp focus, " +
                    // "Color Grading, " +
                    // "Lens Flare " +
                    "ultrarealistic bloom perfect composition  " +
                    " professional concept art intense mood   " +
                    "a highly-detailed dark fantasy masterpiece painting by seb mckinnon, luis royo; " +
                    "deep colors, intricate detail, backlight, trending on artstation, volumetric light ";
            // build += "autumn grove, sylvan realm, 16k, HDR, sharp focus, epic view angle scenic illustration dramatic lighting sublime ultrarealistic bloom perfect composition unreal mythic professional concept art intense mood atmospheric  a highly-detailed dark fantasy masterpiece painting by seb mckinnon, luis royo; deep colors, intricate detail, backlight, trending on artstation, volumetric light ";
            // build += " masterpiece painting by seb mckinnon, luis royo; dramatic lighting epic view angle scenic illustration professional concept art intense mood sublime bloom perfect composition unreal mythic atmospheric    a highly-detailed dark fantasy ; deep colors, ultrarealistic intricate detail, backlight, trending on artstation, volumetric light  ";
            StringSelection selection = new StringSelection(build);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);

            if (!DialogMaster.confirm("Continue?"))
                break;

        }
    }
}






















