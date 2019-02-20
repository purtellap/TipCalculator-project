Wow factor: Made it so that the user can't input incorrect values at all.  Also spent time on making the landscape view look nice.



# TipCalculator-project

## DUE: 17 Feb 2019

## Instructions:

Create an app that takes an input for an amount, an input for number of people spliting the check, a series of radio buttons for pre-determined tip amounts with the last radio button allowing for custom tip ammount, a button to reset the values, and a button to calculate the tip and total amount. The amounts (tip, total, and total per person) must be displayed as a **TextView**. All values must retain its state when the phone is rotated between portrait and landscape modes or any other instances where the app cycles through the activity lifecycle. There should be no values, such as strings, dimensions, etc. hardcoded in the .java files. They should be maintained and accessed from the appropriate resource files. An error message should be displayed to the user if they try to input a bill amount less than $1, total people less than 1, or a tip percentage less than 1%. The code to display an error message is provided in this README.

## Error Message Code:
      Shows the error message in an alert dialog
    
      @param errorMessage
                 String the error message to show
      @param fieldId
                 the Id of the field which caused the error.
                 This is required so that the focus can be
                 set on that field once the dialog is
                 dismissed.
     
    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }


## Key Listener Code:
      KeyListener for the Total Amount, No of People and Other Tip Percentage
      fields. We need to apply this key listener to check for following
      conditions:
     
      1) If user selects Other tip percentage, then the other tip text field
      should have a valid tip percentage entered by the user. Enable the
      Calculate button only when user enters a valid value.
     
      2) If user does not enter values in the Total Amount and No of People,
      we cannot perform the calculations. Hence enable the Calculate button
      only when user enters a valid values.
      
      You will need to add code to the case statements in the switch block.
     
     private OnKeyListener mKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.txtAmount:
                case R.id.txtPeople:
                case R.id.txtTipOther:
            }
            return false;
        }

    };

Feel free to use any Layout you want. ConstrainLayout is a good option, but you may want to experiment with others, such as LinearLayout or TableLayout.

### TODO:
1. Open Android Studio and create an Android project named TipCalculator-counter.
2. Edit .gitignore file and put the neccessary file names that do not belong in GitHub.
3. Create a README.md and put '\#README' in it.
4. VCS -> Import into Version Control -> Share Project on GitHub. Use 'Initial Commit' as commit message.
5. Verify files were uploaded to your GitHub account.
6. Complete the project.
7. Commit and Push your completed project code to GitHub.
8. Print out any source and resource files (in a readable manner) along with screenshots of app working to hand in. Highlight your WOW factors if you have any.
 
 
 ### Grading:
 
 The above requirements are for a 'B'. In order to achieve an 'A', then you must implement a **WOW** factor. This is normally something that you will have to think of and research how to do it yourself. For this first assignment, styling would be considered. 
 
 ***Note:*** Any issues you have with either the GitHub steps or Java/Android questions should be asked publically on Slack. If you have a question, chances are someone else does also. If you see someone has posted a question on Slack and you know the answer, please chime in and answer.

