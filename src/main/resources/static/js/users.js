var sel = document.getElementById('selectroles'),
    button = document.getElementById('button');

button.addEventListener('click', function (e) {
    sel.options[1].selected = true;
    print()
    if ("createEvent" in document) {
        var evt = document.createEvent("HTMLEvents");
        evt.initEvent("change", false, true);
        sel.dispatchEvent(evt);
    }
    else {
        sel.fireEvent("onchange");
    }
});

sel.addEventListener('change', function (e) {
    alert('changed');
});