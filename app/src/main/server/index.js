const express = require('express');
const fs = require('fs');
const mongoose = require("mongoose");
const Router = require("./routes");
const Book = require('./models/Book');
const app = express();
const bodyParser = require('body-parser');
const port = 3001;

app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const username = "IslamicCompass";
const password = "XWPlzv2zRq2jyo9L";
const cluster = "cluster0.ieubd";

//mongodb+srv://IslamicCompass:<password>@cluster0.ieubd.mongodb.net/?retryWrites=true&w=majority

mongoose.connect(
  `mongodb+srv://${username}:${password}@${cluster}.mongodb.net/?retryWrites=true&w=majority`, 
  {
    useNewUrlParser: true,
    useUnifiedTopology: true
    
  } 
  
);

const db = mongoose.connection;
db.on("error", console.error.bind(console, "connection error: "));
db.once("open", () => {
  console.log("Connected successfully");
});


 app.use('/uploads', express.static(__dirname + '/uploads'));



app.use(Router);


app.listen(port, () => {
  console.log(`app listening on port ${port}`)
})