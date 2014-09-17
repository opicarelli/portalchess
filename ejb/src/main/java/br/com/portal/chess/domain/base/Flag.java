package br.com.portal.chess.domain.base;

public enum Flag {
    /**
     * Flag.NO: Ordinal 0, Boolean false.
     */
    NO(false),

    /**
     * Flag.YES: Ordinal 1, Boolean true.
     */
    YES(true);

    private boolean flag;

    private Flag(boolean flag) {
        this.flag = flag;
    }

    public boolean getBooleanValue() {
        return flag;
    }
}
