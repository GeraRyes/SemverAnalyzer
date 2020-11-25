import java.util.Scanner;

public class verificador {
	boolean 	withPreRelease,
	withBuild;
	String 		majorMinorPatch,
	preRelease,
	build;
	String[]	majorMinorPatchList, 
	preReleaseList,
	buildList;

	public boolean master() {
		if (this.comprobarMajorMinorPatch(this.majorMinorPatchList[0])) {
			if (this.comprobarMajorMinorPatch(this.majorMinorPatchList[1])) {
				if (this.comprobarMajorMinorPatch(this.majorMinorPatchList[2])) {

					for (int i = 0; i < this.preReleaseList.length; i++) {
						if(!comprobarPreRelease(this.preReleaseList[i])) { //Fallar preRelease
							return false;
						}
					}

					for (int i = 0; i < this.buildList.length; i++) {
						if(!comprobarBuild(this.buildList[i])) { //Fallar preRelease
							return false;
						}
					}

					return true;

				}else { //Fallar Patch
					return false;
				}     
			}else { //Fallar Minor
				return false;
			}
		}else { //Fallar major
			return false;
		}
	}

	public boolean comprobarPreRelease(String input) {

		if (input.charAt(0)=='0' && input.length()==1) {
			return true;
		}else {
			boolean leadingZero=false;
			boolean isNumeric=true;

			if (input.charAt(0)=='0') {
				leadingZero=true;
			}
			for (int i = 1; i < input.length(); i++) { 
				if (Character.isLetter(input.charAt(i)) || input.charAt(i)=='-') {
					isNumeric=false;
				}else if(Character.isDigit(input.charAt(i))) {

				}else {
					return false;
				}
			} 
			if (leadingZero && isNumeric) {
				return false;
			}else {
				return true;
			}

		}
	}

	public boolean comprobarBuild(String input) {

		for (int i = 1; i < input.length(); i++) { 
			if (!Character.isLetter(input.charAt(i)) && input.charAt(i)!='-' && !Character.isDigit(input.charAt(i))) {
				return false;
			}
		} 
		return true;



	}

	public boolean comprobarMajorMinorPatch(String input) {
		if (input.charAt(0)=='0' && input.length()==1) {
			return true;
		}else if(input.charAt(0)=='0' && input.length()!=1) {
			return false;
		}else {
			for (int i = 0; i < input.length(); i++) { 
				if (!Character.isDigit(input.charAt(i))) {
					return false;
				}
			} 
			return true;
		}
	}


	public boolean verificacionInicial(String input) {

		if (input.contains("..")) {
			return false;
		}

		if (input.contains("-")){ //Por si tenemos que separar en versiones y prerelease

			this.majorMinorPatch=input.split("[-]",2)[0];
			this.majorMinorPatchList=this.majorMinorPatch.split("[.]");


			this.preRelease=input.split("[-]",2)[1];

			if (this.preRelease.contains("+")) {
				this.withBuild=true;
				this.build=this.preRelease.split("[+]",2)[1];
				this.preRelease=this.preRelease.split("[+]",2)[0];

				this.preReleaseList=this.preRelease.split("[.]");
				this.buildList=this.build.split("[.]");
				if (this.build.endsWith(".") || this.preRelease.endsWith(".")) {
					return false;
				}

			}else { //Si además de tener prerelease, tiene build

				this.preReleaseList=this.preRelease.split("[.]");
				this.buildList=new String[0];
				if (this.preRelease.endsWith(".")) {
					return false;
				}
			}



		}else if (input.contains("+")){ //Si solo tiene build

			this.withBuild=true;
			this.majorMinorPatch=input.split("[+]",2)[0];

			this.build=input.split("[+]",2)[1];

			this.majorMinorPatchList=this.majorMinorPatch.split("[.]");
			this.buildList=this.build.split("[.]");
			this.preReleaseList=new String[0];

			if (this.build.endsWith(".")) {
				return false;
			}

		}else { //Si no tiene prerelease ni build

			this.majorMinorPatch=input;
			this.majorMinorPatchList=this.majorMinorPatch.split("[.]");
			this.buildList=new String[0];
			this.preReleaseList=new String[0];
		}

		if (this.majorMinorPatchList.length==3) {
			return true;
		}else {
			return false;
		}
	}


	public static void main(String[] args) {
		verificador a= new verificador();
		
		Scanner sc = new Scanner(System.in); 

		while(true) {
			System.out.println("Ingresa el numero de versión a analizar:");
			String input=sc.nextLine();
			

			if (a.verificacionInicial(input)) {
				if (a.master()) {
					System.out.println("La oración pertenece a semantic versioning 2.0.0");
				}else {
					System.out.println("La oración no pertenece a semantic versioning 2.0.0");
				}
			}else {
				System.out.println("La oración no pertenece a semantic versioning 2.0.0");
			}
			System.out.println("");
		}


	}

}
