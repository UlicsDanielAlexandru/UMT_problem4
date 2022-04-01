
public class StrongPasswordChecker {

    // functie principala de procesare
    public int getMinimumChangesNr(String password) {
        int missingCharacters = getSpecialCharactersMissing(password);
        // daca lungimea e mai mica decat 6, se returneaza maximul dintre numarul de caractere lipsa
        // de anumit tip, sau numarul de caractere lipsa de orice fel
        if(password.length() < 6)
            return Math.max(missingCharacters, 6 - password.length());
        int replacements = 0;
        // printr-o stergere se poate corecta o secventa de 3 caractere identice
        // pentru o secventa de 4 caractere sunt nevoie de 2 stergeri
        // iar pentru 5 este nevoie de 3 stergeri
        int oneReplaceSolver = 0;
        int twoReplaceSolver = 0;
        // se va parcurge parola, si atunci cand se ajung la doua caractere diferite
        // inseamna ca s-a terminat o secventa de caractere identice
        // aceasta variabila ne spune unde s-a incheiat ultima secventa
        int lastRepeatingSequence = 0;
        for(int i = 1; i < password.length(); i++)
        {
            if(password.charAt(i - 1) != password.charAt(i))
            {
                int length = i - lastRepeatingSequence;
                if(length >= 3)
                {
                    // daca lungimea e mai mare decat 3, este nevoie sa inlocuim
                    // doar tot al treilea caracter consecutiv pentru a corecta
                    // secventa
                    replacements += length / 3;
                    // se contorizeaza numarul de caractere necesare pentru
                    // rezolvarea secventelor de 3 si 4 caractere
                    if(length % 3 == 0)
                        oneReplaceSolver++;
                    if(length % 3 == 1)
                        twoReplaceSolver += 2;
                }
                lastRepeatingSequence = i;
            }
        }
        // la final trebuie procesata si ultima secventa din parola
        int length = password.length() - lastRepeatingSequence;
        if(length >= 3)
        {
            replacements += length / 3;
            if(length % 3 == 0)
                oneReplaceSolver++;
            if(length % 3 == 1)
                twoReplaceSolver += 2;
        }
        // daca parola este de lungime corecta trebuie sa facem
        // modificari astfel incat sa avem caracterele speciale
        // sau sa corectam secventele care se repeta
        if(password.length() <= 20)
            return Math.max(missingCharacters, replacements);
        int toBeDeletedCharacters = password.length() - 20;
        // replacements va reprezenta numarul de inlocuiri care trebuie
        // realizate dupa ce s-au sters toate caracterele aditionale
        // acest trebuie drecementat deoarece stergeriile rezolva anumite inlocuiri
        replacements -= Math.min(toBeDeletedCharacters, oneReplaceSolver);
        replacements -= Math.min(Math.max(toBeDeletedCharacters - oneReplaceSolver, 0), twoReplaceSolver) / 2;
        replacements -= Math.max(toBeDeletedCharacters - oneReplaceSolver - twoReplaceSolver, 0) / 3;
        return toBeDeletedCharacters + Math.max(missingCharacters, replacements);
    }

    // functie care returneaza tipurile diferete de caractere care lipsesc: litera mare, mica sau cifra
    private int getSpecialCharactersMissing(String password) {
        int upperMissing = password.chars().anyMatch(Character::isUpperCase) ? 0 : 1;
        int lowerMissing = password.chars().anyMatch(Character::isLowerCase) ? 0 : 1;
        int digitMissing = password.chars().anyMatch(Character::isDigit) ? 0 : 1;
        return upperMissing + lowerMissing + digitMissing;
    }

}
