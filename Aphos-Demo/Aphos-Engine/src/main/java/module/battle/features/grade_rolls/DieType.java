package module.battle.features.grade_rolls;

public enum DieType{
    d4(4),
    d6(6),
    d8(8),
    d10(10),
    d12(12),
    d20(20),
    ;
    public  final int value;

    DieType(int value) {
        this.value = value;
    }
}
