function ggetMachines() {
    let location = document.getElementById('locationList').value;
    fetch(`/machines?locationList=${location}`).then(response => response.json()).then((messages) => {
    const cmmtListEl = document.getElementById('machine-container');
    messages.forEach((message) => {
        cmmtListEl.appendChild(createMachineElement(message));
    })
  });

}

function createMachineElement(machine) {
  const machineElement = document.createElement('li');
  machineElement.className = 'machine';

  const titleElement = document.createElement('span');
  titleElement.innerText = machine.testing;

  machineElement.appendChild(titleElement);
  
  return machineElement;
}