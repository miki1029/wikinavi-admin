function renderTile(xGridNum, yGridNum, imageAreaWidth, imageAreaHeight) {
    var i, j;
    var $tileArea = $('#tileArea');
    var tileWidth = imageAreaWidth / xGridNum;
    var tileHeight = imageAreaHeight / yGridNum;

    $tileArea.empty();

    for (j = 0; j < yGridNum; j++) {
        var $tileRow = $('<div class="tileRow"></div>');
        $tileRow.appendTo($tileArea);
        for (i = 0; i < xGridNum; i++) {
            var $tile = $('<div class="tile" data-x="' + (i + 1) + '" data-y="' + (j + 1) + '"></div>');
            $tile.css({
                top: tileHeight * j,
                left: tileWidth * i,
                width: tileWidth,
                height: tileHeight
            });
            $tile.appendTo($tileRow);
        }
    }

}