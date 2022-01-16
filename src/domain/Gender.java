package domain;

//Gender  constants, used for student type objects
public enum Gender {
    M {
        /*
         * For a beter user experience, casting the full string representation of *the M
         * constant. toString is used, so that ComboBoxes<Gender> can still be
         * applied. The .Name() method is used whenever the actual constant needs * * to
         * be used (for example when creating a student)
         */
        @Override
        public String toString() {
            return "Male";
        }
    },
    F {
        /*
         * For a beter user experience, casting the full string representation of *the F
         * constant. toString is used, so that ComboBoxes<Gender> can still be
         * applied. The .Name() method is used whenever the actual constant needs * * to
         * be used (for example when creating a student)
         */
        @Override
        public String toString() {
            return "Female";
        }
    },
    O {
        /*
         * For a beter user experience, casting the full string representation of *the O
         * constant. toString is used, so that ComboBoxes<Gender> can still be
         * applied. The .Name() method is used whenever the actual constant needs * * to
         * be used (for example when creating a student)
         */
        @Override
        public String toString() {
            return "Other";
        }
    }
}
