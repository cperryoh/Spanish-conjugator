


public enum stemChangers {
	None("none"),IE("E to ie"),I("E to I"),UE("O to ue"),YOGo("Yo go"),wack("wack");
	public String stemChange;
    private stemChangers(String change) {
        stemChange = change;
    }
}
