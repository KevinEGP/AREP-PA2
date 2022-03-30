const host = `http://localhost`;
const port = 3500

function log(){
  let value =  $(`#value`).val();
  $.ajax({
    method: 'GET',
    url:`${host}:${port}/log?value=${value}`,
    success: function(data) {
      // console.log(JSON.parse(data));
      res = JSON.parse(data);
      console.log(res);
      $(`#result`).text('El resultado es: ' + res.output);
    },
    error: function(error) {
      $(`#result`).text('Se produjo un error');
    }
  })
}

function cos(){
  let value =  $(`#value`).val();
  $.ajax({
    method: 'GET',
    url:`${host}:${port}/cos?value=${value}`,
    success: function(data) {
      // console.log(JSON.parse(data));
      res = JSON.parse(data);
      console.log(res);
      $(`#result`).text('El resultado es: ' + res.output);
    },
    error: function(error) {
      $(`#result`).text('Se produjo un error');
    }
  })
}