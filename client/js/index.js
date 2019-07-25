x = 2;

function submit() {
    var formData  = new FormData();
    formData.append('attributes', document.getElementById('attributes').files[0]);
    formData.append('rules', document.getElementById('rules').files[0]);
  
    fetch('/api/upload', {
      method: 'POST',
      body: formData
    }).then(response => response.json())
    .then(receive);
        
}

function receive(data) {
    x = data;
    document.getElementById('response').innerHTML = data;
}