function initializeSse() {
    const source = new EventSource('/emitter');
    source.addEventListener('message', (e) => {
        $('#acknowledgeConfirmation').attr("style", "display: block;");
        $('#confirmMessage').text(e.data);
    });

    source.addEventListener('error', (e) => {
        if (e.readyState == EventSource.CLOSED) {
            console.log("Closed");
        } else {
            $('#rejectedConfirmation').attr("style", "display: block;");
        }
    });
}

function initializeResultSse() {
    const source = new EventSource('/emitter');
    source.addEventListener('message', (e) => {
        $('#approval').attr("style", "display: block;");
        $('#approveMessage').text(e.data);
    });

    source.addEventListener('error', (e) => {
        if (e.readyState == EventSource.CLOSED) {
            console.log("Closed");
        } else {
            $('#reject').attr("style", "display: block;");
            $('#rejectMessage').text(e.data);
        }
    });
}

function dismissModal() {
    $('#acknowledgeConfirmation').attr("style", "display: none;");
    $('#rejectedConfirmation').attr("style", "display: none;");
}