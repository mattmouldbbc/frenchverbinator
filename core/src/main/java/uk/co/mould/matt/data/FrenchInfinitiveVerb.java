package uk.co.mould.matt.data;

public final class FrenchInfinitiveVerb {
	private String infinitiveAsString;

	private FrenchInfinitiveVerb(String infinitiveAsString) {
		this.infinitiveAsString = infinitiveAsString;
	}

	public static FrenchInfinitiveVerb fromString(String infinitiveAsString) {
		return new FrenchInfinitiveVerb(infinitiveAsString);
	}

	@Override
	public String toString() {
		return infinitiveAsString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FrenchInfinitiveVerb that = (FrenchInfinitiveVerb) o;

		return !(infinitiveAsString != null ? !infinitiveAsString.equals(that.infinitiveAsString) : that.infinitiveAsString != null);

	}

	@Override
	public int hashCode() {
		return infinitiveAsString != null ? infinitiveAsString.hashCode() : 0;
	}

}
