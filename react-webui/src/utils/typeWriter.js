const typeWriter = (setCapsuleText, text) => {
  let i = 0;

  const type = () => {
    if (i < text.length) {
      setCapsuleText(text.substring(0, i + 1));
      i++;
      setTimeout(type, 130);
    }
  };

  type();
};

export default typeWriter;
