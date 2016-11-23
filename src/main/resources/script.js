var uri = "ws://" + location.host + '/api';
var webSocket = null;

window.onload = function() {
  ConnectionHandler.checkConnection();
  setInterval(ConnectionHandler.checkConnection, 1000);
  open();
  MessageBox.setUpMessageBox();
};

// WebSocket start
function open() {
  if (webSocket == null) {
    webSocket = new WebSocket(uri);
    webSocket.onopen    = onOpen;
    webSocket.onmessage = onMessage;
    webSocket.onclose   = onClose;
    webSocket.onerror   = onError;
  }
}

function onOpen(event) {
	console.log('connecting');
}

function onMessage(event) { 
	if (!event || !event.data) return;
	console.log(event.data); 
}

function onClose(event) {
	console.log('disconnected!');
}

function onError(event) {
	console.log('error!');
}
// WebSocket end


// MessageBox start
MessageBox = {
  'setUpMessageBox': function() {
    var sendMessage = document.getElementById('submitMessage');
    sendMessage.addEventListener('click', MessageBox.submitMessage);
  },
  'submitMessage': function(e) {
    e.preventDefault();
    var action = document.querySelector('select[name="action"]').value;
    var keys   = document.querySelectorAll('input[name="data[key][]"]');
    var vals   = document.querySelectorAll('input[name="data[val][]"]');
    var data   = {};
    for (var i = 0; i < keys.length; i++) {
      data[keys[i].value]  = vals[i].value;
    }

    var message = {
      'action': action,
      'data'  : data,
    };
    var jsonString = JSON.stringify(message);
    webSocket.send(jsonString);
    MessageBox.displayMessage(jsonString, null, 2);
  },
  'displayMessage': function(message) {
    var itemList = document.getElementById('itemList');
    var item = document.createElement('li');
    item.innerText = message;
    itemList.insertBefore(item, itemList.childNodes[0]);
  },
}
// MessageBox end


// ConnectionHandler start
ConnectionHandler = {
  'connectionStatus': document.getElementById('connectionStatus'),
  'connecting'   : function() { 
    connectionStatus.innerText   = 'connecting...'; 
    connectionStatus.style.color = 'red';
  },
  'open'      : function() {
    connectionStatus.innerText = 'open';
    connectionStatus.style.color = 'lime';
  },
  'closing'   : function() { 
    connectionStatus.innerText   = 'closing...'; 
    connectionStatus.style.color = 'red';
  },
  'closed'   : function() {
    connectionStatus.innerText = 'closed';
    connectionStatus.style.color = 'red';
  },
  'checkConnection': function() {
    if (webSocket == null) {
      ConnectionHandler.connecting();
      return;
    }
    
    switch (webSocket.readyState) {
      case 0:
        ConnectionHandler.connecting();
        break;
      case 1:
        ConnectionHandler.open();
        break;
      case 2:
        ConnectionHandler.closing();
        break;
      case 3:
        ConnectionHandler.closed();
        break;
    }
  }
}
// ConnectionHandler end