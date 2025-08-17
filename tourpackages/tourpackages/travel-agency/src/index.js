const API_BASE = 'http://localhost:8087/api/packages';



// Track current slide index for each package
const slideIndices = [];

// Show the nth slide of the slideshow for package index
function showSlides(n, index) {
  const slides = document.getElementsByClassName(`slide-${index}`);
  if (!slides.length) return;

  if (n >= slides.length) slideIndices[index] = 0;
  else if (n < 0) slideIndices[index] = slides.length - 1;
  else slideIndices[index] = n;

  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  slides[slideIndices[index]].style.display = "block";
}

// Handle previous/next slide clicks
function plusSlides(n, index) {
  if (typeof slideIndices[index] === 'undefined') slideIndices[index] = 0;
  showSlides(slideIndices[index] + n, index);
}
window.plusSlides = plusSlides; // Make accessible to HTML
window.showSlides = showSlides;

// Render packages
function renderPackages(packages, targetId) {
  const list = document.getElementById(targetId);
  list.innerHTML = '';
  if (!packages.length) {
    list.innerHTML = '<p>No packages found.</p>';
    return;
  }

  packages.forEach((pkg, index) => {
    const div = document.createElement('div');
    div.className = 'package-card';

    let slideHTML = '';
    if (pkg.imageUrls && pkg.imageUrls.length) {
      slideHTML = `
        <div class="slideshow-container" id="slideshow-${index}">
          ${pkg.imageUrls.map((url, i) => `
            <div class="mySlides slide-${index}" style="${i === 0 ? 'display:block' : 'display:none'}">
              <img src="${url}" class="slide-image" alt="Package Image">
            </div>
          `).join('')}
          <a class="prev" onclick="plusSlides(-1, ${index})">&#10094;</a>
          <a class="next" onclick="plusSlides(1, ${index})">&#10095;</a>
        </div>
      `;
      slideIndices[index] = 0;
    }

div.innerHTML = `
  ${slideHTML}
  <h3>${pkg.name || 'No Name'}</h3>
  <div><strong>Destination:</strong> ${pkg.destination}</div>
  <div><strong>Duration:</strong> ${pkg.duration} days</div>
  <div><strong>Price:</strong> ₹${pkg.price}</div>
  <div>${pkg.description || ''}</div>
  <div>${pkg.itinerary ? `<em>${pkg.itinerary}</em>` : ''}</div>
  
`;
const button = document.createElement('button');
button.textContent = 'View Details';
button.addEventListener('click', () => viewPackageDetails(pkg));
div.appendChild(button);


    list.appendChild(div);

  });
}

function viewPackageDetails(pkg) {
  const modal = document.getElementById('packageDetails');

  const packageId = pkg.id || pkg._id || ''; // fallback if id is stored under _id

  modal.innerHTML = `
    <div class="modal-content">
      <span class="close" title="Close" onclick="document.getElementById('packageDetails').style.display='none'">&times;</span>
      
      <h2>${pkg.name || 'Unnamed Package'}</h2>
      
      <p><strong>Destination:</strong> ${pkg.destination}</p>
      <p><strong>Duration:</strong> ${pkg.duration} days</p>
      <p><strong>Price:</strong> ₹${pkg.price}</p>
      <p><strong>Description:</strong> ${pkg.description || 'N/A'}</p>
      <p><strong>Itinerary:</strong> ${pkg.itinerary || 'N/A'}</p>

      <div class="images-section">
        <strong>Images:</strong>
        <div class="images-container">
          ${
            pkg.imageUrls && pkg.imageUrls.length
              ? pkg.imageUrls.map(url => `
                  <img src="${url}" alt="Package Image" class="package-image">
                `).join('')
              : 'No images'
          }
        </div>
      </div>
 <button class="book-now-btn" id="bookNowBtn">Book Now</button>
    </div>
  `;

  // Add event listener after adding modal content to DOM
  const bookBtn = document.getElementById('bookNowBtn');
  bookBtn.addEventListener('click', () => {
    window.location.href = `booking.html?id=${packageId}`;
  });
  modal.style.display = 'block';
}



window.viewPackageDetails = viewPackageDetails;




// Fetch and display all packages
function fetchAllPackages() {
  fetch(`${API_BASE}/all`)
    .then(res => res.json())
    .then(data => renderPackages(data, 'packageList'));
}

// Add package
function addPackage(pkg) {
  fetch(`${API_BASE}/add`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(pkg)
  })
  .then(res => res.json())
  .then(() => {
    alert('Package added!');
    fetchAllPackages();
  });
}

// Update package
function updatePackage(id, pkg) {
  fetch(`${API_BASE}/update/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(pkg)
  })
  .then(res => res.json())
  .then(() => {
    alert('Package updated!');
    fetchAllPackages();
  });
}

// Delete package
function deletePackage(id) {
  fetch(`${API_BASE}/delete/${id}`, { method: 'DELETE' })
    .then(res => {
      if (res.ok) {
        alert('Deleted successfully!');
        fetchAllPackages();
      } else {
        alert('Delete failed.');
      }
    });
}

// Search packages
function searchPackages(keyword) {
  fetch(`${API_BASE}/search?keyword=${encodeURIComponent(keyword)}`)
    .then(res => res.json())
    .then(data => renderPackages(data, 'searchResults'));
}

// Filter packages
function filterPackages({ destination, minPrice, maxPrice, minDays, maxDays }) {
  let params = [];
  if (destination) params.push(`destination=${encodeURIComponent(destination)}`);
  if (minPrice) params.push(`minPrice=${minPrice}`);
  if (maxPrice) params.push(`maxPrice=${maxPrice}`);
  if (minDays) params.push(`minDays=${minDays}`);
  if (maxDays) params.push(`maxDays=${maxDays}`);
  fetch(`${API_BASE}/filter?${params.join('&')}`)
    .then(res => res.json())
    .then(data => renderPackages(data, 'filterResults'));
}

// Show specific section
function showSection(section) {
  document.querySelectorAll('.section').forEach(sec => sec.style.display = 'none');
  document.getElementById(section + '-section').style.display = 'block';
  if (section === 'view') fetchAllPackages();
}
window.showSection = showSection;

// Dynamically add image input field
function addImageUrlField(containerId) {
  const container = document.getElementById(containerId);
  if (!container) {
    console.error(`Container with ID '${containerId}' not found.`);
    return;
  }
  const input = document.createElement("input");
  input.type = "text";
  input.className = "image-url";
  input.placeholder = "Image URL";
  container.appendChild(input);
}
window.addImageUrlField = addImageUrlField;

// Form Event Listeners
document.addEventListener('DOMContentLoaded', function () {
  showSection('view');

  // Add form
  document.getElementById('addForm').onsubmit = function (e) {
    e.preventDefault();
    const pkg = {
      name: document.getElementById('addName').value,
      destination: document.getElementById('addDestination').value,
      duration: document.getElementById('addDuration').value,
      price: document.getElementById('addPrice').value,
      description: document.getElementById('addDescription').value,
      itinerary: document.getElementById('addItinerary').value,
      imageUrls: Array.from(document.querySelectorAll('#addImageUrlsContainer .image-url'))
        .map(input => input.value.trim())
        .filter(url => url !== '')
    };
    addPackage(pkg);
    this.reset();
    showSection('view');
  };

  // Update form
  document.getElementById('updateForm').onsubmit = function (e) {
    e.preventDefault();
    const id = document.getElementById('updateId').value;
    const pkg = {
      name: document.getElementById('updateName').value,
      destination: document.getElementById('updateDestination').value,
      duration: document.getElementById('updateDuration').value,
      price: document.getElementById('updatePrice').value,
      description: document.getElementById('updateDescription').value,
      itinerary: document.getElementById('updateItinerary').value,
      imageUrls: Array.from(document.querySelectorAll('#updateImageUrlsContainer .image-url'))
        .map(input => input.value.trim())
        .filter(url => url !== '')
    };
    updatePackage(id, pkg);
    this.reset();
    showSection('view');
  };

  // Delete form
  document.getElementById('deleteForm').onsubmit = function (e) {
    e.preventDefault();
    const id = document.getElementById('deleteId').value;
    deletePackage(id);
    this.reset();
    showSection('view');
  };

  // Search
  document.getElementById('searchBtn').onclick = function () {
    const keyword = document.getElementById('searchKeyword').value;
    searchPackages(keyword);
  };

  // Filter
  document.getElementById('filterBtn').onclick = function () {
    filterPackages({
      destination: document.getElementById('filterDestination').value,
      minPrice: document.getElementById('filterMinPrice').value,
      maxPrice: document.getElementById('filterMaxPrice').value,
      minDays: document.getElementById('filterMinDays').value,
      maxDays: document.getElementById('filterMaxDays').value
    });
  };
});


document.getElementById('voiceSearchBtn').addEventListener('click', () => {
  // Check for browser support
  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
  if (!SpeechRecognition) {
    alert('Your browser does not support Speech Recognition.');
    return;
  }

  const recognition = new SpeechRecognition();
  recognition.lang = 'en-US'; // You can change the language if you want
  recognition.interimResults = false;
  recognition.maxAlternatives = 1;

  recognition.start();

  recognition.onresult = (event) => {
    let speechResult = event.results[0][0].transcript;

    // Remove trailing punctuation (period, comma, exclamation, question mark)
    speechResult = speechResult.replace(/[.,!?]$/, '');

    document.getElementById('searchKeyword').value = speechResult;
    // Optionally auto-trigger search
    searchPackages(speechResult);
  };

  recognition.onerror = (event) => {
    console.error('Speech recognition error:', event.error);
    alert('Speech recognition error: ' + event.error);
  };
});

const voiceBtn = document.getElementById('voiceSearchBtn');

voiceBtn.addEventListener('click', () => {
  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
  if (!SpeechRecognition) {
    alert('Your browser does not support Speech Recognition.');
    return;
  }

  const recognition = new SpeechRecognition();
  recognition.lang = 'en-US';
  recognition.interimResults = false;
  recognition.maxAlternatives = 1;

  recognition.start();
  voiceBtn.classList.add('glowing'); // Add glow on start

  recognition.onresult = (event) => {
    let speechResult = event.results[0][0].transcript;
    speechResult = speechResult.replace(/[.,!?]$/, '');
    document.getElementById('searchKeyword').value = speechResult;
    searchPackages(speechResult);
  };

  recognition.onerror = (event) => {
    console.error('Speech recognition error:', event.error);
    alert('Speech recognition error: ' + event.error);
  };

  recognition.onend = () => {
    voiceBtn.classList.remove('glowing'); // Remove glow when done
  };
});

// Show/hide chatbot window
const chatbotToggleBtn = document.getElementById('chatbotToggleBtn');
const chatbot = document.getElementById('chatbot');
const chatbotCloseBtn = document.getElementById('chatbotCloseBtn');
const chatbotMessages = document.getElementById('chatbotMessages');
const chatbotForm = document.getElementById('chatbotForm');
const chatbotInput = document.getElementById('chatbotInput');

chatbotToggleBtn.addEventListener('click', () => {
  chatbot.style.display = 'flex';
  chatbotInput.focus();
});

chatbotCloseBtn.addEventListener('click', () => {
  chatbot.style.display = 'none';
});

// Add message to chat window
function addMessage(text, sender = 'bot') {
  const msgDiv = document.createElement('div');
  msgDiv.style.margin = '8px 0';
  msgDiv.style.padding = '8px 12px';
  msgDiv.style.borderRadius = '15px';
  msgDiv.style.maxWidth = '80%';
  msgDiv.style.clear = 'both';
  msgDiv.style.fontSize = '14px';

  if (sender === 'user') {
    msgDiv.style.background = '#007bff';
    msgDiv.style.color = 'white';
    msgDiv.style.float = 'right';
  } else {
    msgDiv.style.background = '#f1f1f1';
    msgDiv.style.color = '#333';
    msgDiv.style.float = 'left';
  }
  msgDiv.textContent = text;
  chatbotMessages.appendChild(msgDiv);
  chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
}

// Basic bot response logic
function getBotResponse(message) {
  const msg = message.toLowerCase();

  if (msg.includes('hello') || msg.includes('hi')) {
    return 'Hello! How can I help you with travel packages today?';
  }
  if (msg.includes('destination')) {
    return 'We offer packages to popular destinations like Goa, Kerala, and Manali.';
  }
  if (msg.includes('price')) {
    return 'Our packages range from ₹5,000 to ₹50,000 depending on duration and destination.';
  }
  if (msg.includes('duration') || msg.includes('days')) {
    return 'Our packages vary from 3 to 15 days. Let me know your preference!';
  }
  if (msg.includes('book') || msg.includes('booking')) {
    return 'You can book a package by clicking the "View Details" button on any package and then the "Book Now" button.';
  }
  if (msg.includes('thank')) {
    return 'You’re welcome! Feel free to ask if you have more questions.';
  }
  return "Sorry, I didn't understand that. You can ask about destinations, price, duration, or booking.";
}

// Handle form submit
chatbotForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const userMsg = chatbotInput.value.trim();
  if (!userMsg) return;

  addMessage(userMsg, 'user');
  chatbotInput.value = '';

  // Simulate bot thinking delay
  setTimeout(() => {
    const botReply = getBotResponse(userMsg);
    addMessage(botReply, 'bot');
  }, 500);
});
