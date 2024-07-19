function initializeQueueSse() {
    const source = new EventSource('/emitter');
    source.addEventListener('message', (e) => {
        $('#pending').attr("style", "display: block;");
        $('#pendingMessage').text(e.data);
    });

    source.addEventListener('error', (e) => {
        if (e.readyState == EventSource.CLOSED) {
            console.log("Closed");
        } else {
            initializeQueueSse();
        }
    });
}