const express = require('express');
const app = express();

const PORT = 3333;

app.listen(PORT, () => {
  console.log(`Started on port ${PORT}`);
})
//First GET Route
app.post('/validate', (req, resp) => {
  //Get The Data From The DB And Validate
});

app.get('/e (req, resp) => {
  //Get the statistics data from the database and display  it for devs
})
