var tmp_filename;

function loadAttributes() {
  reader = new FileReader();
  reader.onload = function() {
      var data = JSON.parse(reader.result);
      for (var i = 0; i < data.length; i++) {
        data[i].id = i+1;
      }
      app.attributes = data;
  }
  reader.readAsText(document.getElementById('attributes').files[0]);
}

function loadCharacteristics() {
    var unknown = 'UNKNOWN'; // must be the same identifier as in the rules XML file
    for (rule of app.rules) {
        for (name in rule.characteristics) {
            value = rule.characteristics[name];
            app.characteristics.n
            app.characteristics[name].min = min(app.characteristics[name].min, value);
        }
    }
}

function receive(data) {
  for (var i = 0; i < data.length; i++) {
    data[i].id = i+1;
  }
  app.rules = data;
  app.activetab = 2;
  app.loadMsg = 'Current file: ' + tmp_filename;
}

function browseAttributes() { document.getElementById('attributes').click(); }
function browseRules() { document.getElementById('rules').click(); }
function setAttributes() {
  file = document.getElementById('attributes').files[0];
  document.getElementById('attributesText').value = file.name;
}
function setRules() {
  file = document.getElementById('rules').files[0];
  document.getElementById('rulesText').value = file.name;
  tmp_filename = file.name;
}
function submit() {
  var attributes = document.getElementById('attributes').files[0];
  var rules = document.getElementById('rules').files[0];

  if (attributes == undefined) {
      app.loadMsg = 'Load json file with attributes before submit.';
  } 
  else if (rules == undefined) {
      app.loadMsg = 'Load xml file with rules before submit.';
  }
  else {
      loadAttributes();
      var formData  = new FormData();
      formData.append('attributes', attributes);
      formData.append('rules', rules);

      fetch('/api/upload', {
        method: 'POST',
        body: formData
      }).then(response => response.json())
      .then(receive);
      loadCharacteristics();
  }
}

function loadDemo() {
  tmp_filename = 'Demo file';
  fetch('./data/attributes.json', {
    method: 'GET'
  }).then(response => response.json())
  .then(function(data) {
    app.attributes = data;
  });
  fetch('/data/rules.json', {
    method: 'GET'
  }).then(response => response.json())
  .then(receive); 
  app.activetab = 2;
}