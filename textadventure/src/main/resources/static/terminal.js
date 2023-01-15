let caretPosition = 47;     // In pixels
const userInput = $('#user-input');
const caret = $('#caret');


$(function() {
    userInput.on('keydown', function(event) {
        caretPosition = getCaretPosition();
        if(getCaretPosition() > 47 && event.key === 'ArrowLeft') {
            stopBlinking();
            let width = userInput[0].offsetWidth;
            if(caretPosition <= width) {
                caretPosition = (--userInput[0].selectionStart * 9.6016) + 47;
                moveCaret(caretPosition);
            }
        }
        else if(caretPosition < userInput.val().length * 9.6016 + 47 && event.key === 'ArrowRight') {
            stopBlinking();
            let width = userInput[0].offsetWidth;
            if(caretPosition <= width) {
                caretPosition = ((userInput[0].selectionStart + 1) * 9.6016) + 47;
                moveCaret(caretPosition);
            }
        }
    });

    // Move blinking caret to index in text where user clicked
    userInput.on('click', function() {
        caretPosition = (userInput[0].selectionStart * 9.6016) + 47;
        let width = userInput[0].offsetWidth;
        if(getCaretPosition() <= width) {
            moveCaret(caretPosition);
        }
    });

    // Like a regular caret, stop blinking while typing. Move caret over
    userInput.on('input', function() {
        stopBlinking();
        let input = userInput.val();
        input = input.replace(/\s/g, '&nbsp;');
        caretPosition = (userInput[0].selectionStart * 9.6016) + 47;
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
        caret[0].style.transform = 'translateX(' + pixels + 'px) translateY(-47px)';
    }

    function stopBlinking() {
        caret.toggleClass('caret-solid');
        caret.toggleClass('caret-blink');
        setTimeout(function() {
            caret.toggleClass('caret-blink');
            caret.toggleClass('caret-solid');
        }, 250);
    }

    function getCaretPosition() {
        return parseInt(caret.css('-webkit-transform').split(/[(),]/)[5]);
    }
});

/* <script>$('#user-input').val('hacked');</script> */

/* <script>setTimeout(function() {$('#user-input').val('hacked');}, 9.60160);</script> */
/* <script>console.log('log');</script> */