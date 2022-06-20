const express = require("express");
const multer = require('multer');
const userModel = require("./models/User");
const bookModel = require("./models/Book");
const path = require('path');
const app = express();

app.post("/add_user", async (request, response) => {
    const user = new userModel(request.body);
  
    try {
      await user.save();
      response.send(user);
    } catch (error) {
      response.status(500).send(error);
    }
});

app.get("/users", async (request, response) => {
    const users = await userModel.find({});
  
    try {
      response.send(users);
    } catch (error) {
      response.status(500).send(error);
    }
  });

  //The reason this was not working is because the ':' in the date 
// has issues in windows for some reason, so I replaced it with '-'

  const storage = multer.diskStorage({    
    destination: function (req, file, cb) {  
      cb(null, path.join(__dirname, 'uploads'));   
        // cb(null,  './uploads');
      
  
    },
     
    filename: function (req, file, cb) {      
  
    cb(null, Date.now() + "--" + file.originalname)
    }  
  })
  
  const upload = multer({ storage: storage });
  
  // app.post('/upload', upload.single('myFile'), async(req, res, next) => {
  //   const file = req.file
  //   if (!file) {
  //     const error = new Error('Please upload a file')
  //     error.httpStatusCode = 400
  //     return next("hey error")
  //   }
      
  //     // Would the following code not be best suited for the GET requests
  //     // Try implementing the following code into the GET request and 
  //     // Comment this version out. 
  //     const imagepost = new model({
  //       image: file.path
  //     })
  //     const savedimage = await imagepost.save()
  //     res.json(savedimage)
  //     console.log(req.file);
    
  // })

  // app.get('/image', async (req, res) => {
//   const image = await model.find()
//   console.log(res);
//   //res.json(image)
//   image.forEach((data) => {
//     // console.log("data " + data.image.split("--").pop()); 
//     // console.log("data " + data.image.split("\\").pop()); 
//     var temp = data.image.split("uploads").pop(); 
//     data.image = path.join(__dirname, ".", "uploads") + temp;
//     console.log("data " + data.image);
//     // res.sendFile(data.image);
//     //var theImage = path.join(__dirname, ".", "uploads") + data;
//     //res.json(theImage);
//     // res.sendFile(theImage);
//   })
//   res.json(image);
  
//  })

// app.get('/image', async (req, res) => {
//   const image = await models.find()
//   console.log(res);
//   const imageObj = {
//     kaabaDirection: "uploads/1653502182126--kaabaDirection.png",
//     arrow: "uploads/arrow1.png"
//   };

//   image.forEach((data) => {
    
//   })
//   res.sendFile(path.join(__dirname, imageObj.arrow));
  
//  })


// app.get('/', (req, res) => {
//  // res.send('Hello World!');

//  console.log("Android has sent a GET REQUEST!!!");

//   var succ = [
//     {
//       "userId": 1,
//       "id": 1,
//       "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
//       "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
//     },
//     {
//       "userId": 1,
//       "id": 2,
//       "title": "qui est esse",
//       "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
//     }
//   ]; 

//   res.send(succ); 
// })



app.post('/book', async (req, res) => {
  if(req == null){
      const error = new Error('Please post book details')
      error.httpStatusCode = 400
  }

  const bookPost = new bookModel(req.body);
  
  try {
    await bookPost.save();
    res.send(bookPost);
  } catch (error) {
    res.status(500).send(error);
  }
   console.log(bookPost);
})

app.get('/books', async (req, res) => {

  const books = await bookModel.find({});
  
  try {
    res.send(books);
  } catch (error) {
    res.status(500).send(error);
  }
  console.log(books);
 })

 app.get('/books/:Book_Name', (req, res) => {
  const bookName = req.params.Book_Name
   const filepath = path.join(__dirname, `/uploads/${bookName}.pdf`)
  //  try {
  //   //  res.sendFile(filepath); 
  //   res.send(filepath); 
  // } catch (error) {
  //   res.status(500).send(error);
  // }
  // console.log(filepath);

    //  res.sendFile(filepath); 
    res.sendFile(filepath); 

    console.log("filepath: " + filepath);
 })
  
  module.exports = app;