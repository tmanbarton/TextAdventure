let caretPosition = 40;     // In pixels
const userInput = $('#user-input');
const caret = $('#caret');


$(function() {
    userInput.on('keydown', function(event) {
        // Move the caret when left or right arrow keys are pressed
        if(caretPosition !== 40 && event.key === 'ArrowLeft') {
            stopBlinking();
            // All these * 8 because ubuntu mono is 8px wide
            caretPosition = (userInput[0].selectionStart * 8) + 32;
            let width = userInput[0].offsetWidth;
            if(caretPosition <= width) {
                moveCaret(caretPosition);
            }
        }
        else if(caretPosition < userInput.val().length * 8 + 40 && event.key === 'ArrowRight') {
            stopBlinking();
            caretPosition = ((userInput[0].selectionStart + 1) * 8) + 40;
            let width = userInput[0].offsetWidth;
            if(caretPosition <= width) {
                moveCaret(caretPosition);
            }
        }
    });

    // Move blinking caret to index in text where user clicked
    userInput.on('click', function() {
        caretPosition = (userInput[0].selectionStart * 8) + 40;
        let width = userInput[0].offsetWidth;
        if(caretPosition <= width) {
            moveCaret(caretPosition);
        }
    });

    // Like a regular caret, stop blinking while typing. Move caret over
    userInput.on('input', function() {
        stopBlinking();
        let input = userInput.val();
        input = input.replace(/\s/g, '&nbsp;');
        caretPosition = (userInput[0].selectionStart * 8) + 40;
        let width = userInput[0].offsetWidth;
        if(caretPosition <= width) {
            moveCaret(caretPosition);
        }
    });

    // Focus on input no matter when in the terminal window user clicks
    $('#terminal').on('click', function() {
        userInput.focus();
    });

    function moveCaret(pixels) {
        caret[0].style.transform = 'translateX(' + pixels + 'px) translateY(-46px)';
    }

    function stopBlinking() {
        caret.toggleClass('caret-solid');
        caret.toggleClass('caret-blink');
        // caret.id = 'caret-solid';
        setTimeout(function() {
            caret.toggleClass('caret-blink');
            caret.toggleClass('caret-solid');
        }, 250);
    }

});

/* <script>$('#user-input').val('hacked');</script> */

/* <script>setTimeout(function() {$('#user-input').val('hacked');}, 100);</script> */
/* <script>console.log('log');</script> */