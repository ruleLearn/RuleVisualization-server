function load() {
    rules = document.getElementById('rules').files[0];
    var reader = new FileReader();
    reader.readAsText(rules);
    doc = new DOMParser().parseFromString(reader.result, "text/xml");
}