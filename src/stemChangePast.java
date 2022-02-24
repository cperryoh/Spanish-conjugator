

public enum stemChangePast {
	None("none"),I("E to I"),U("O to u");
	public String stemChange;
    private stemChangePast(String change) {
        stemChange = change;
    }
}
