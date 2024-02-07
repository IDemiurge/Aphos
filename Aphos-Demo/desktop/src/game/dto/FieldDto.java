package game.dto;

import system.path.Resource;

/**
 * Created by Alexander on 11/5/2023
 */
public class FieldDto extends GdxDto {
    //it's use will define prefix? We want to be able
    //maybe here is the deal - define prefix field, and if it's null - use default!
    //aha - so for any resource, use path/name/format ?
    //to ignore format if it's atlas or so

    //resolve textures not just from string - but from pathData !


    public FieldDto(String background) {
        // resources.put("background", ResFactory.get(Resource.texture_background, background));
    }
}
