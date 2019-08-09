# keyboard_listener

listener android keyboard status plugin.

# Usage

```
@override
  void initState() {
    super.initState();
  // isShow  true(keyboard show) false(keyboard hide)
  KeyBoardReceive.getKeyBoardStatus((isShow) {
      setState(() {
        
      });
    });
  }
  
  
```