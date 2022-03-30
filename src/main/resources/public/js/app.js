const host = `http://ec2-34-226-124-23.compute-1.amazonaws.com/`;
const port = 8080

function log(){
  let value =  $(`#value`).val();
  $.ajax({
    method: 'GET',
    url:`${host}:${port}/log?value=${value}`,
    success: function(data) {
      // console.log(JSON.parse(data));
      res = JSON.parse(data);
      console.log(res);
      $(`#result`).text('El resultado es: ' + res);
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
      $(`#result`).text('El resultado es: ' + res);
    },
    error: function(error) {
      $(`#result`).text('Se produjo un error');
    }
  })
}