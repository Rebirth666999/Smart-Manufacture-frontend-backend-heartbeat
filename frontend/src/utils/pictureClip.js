/**
 * 图片裁剪边缘
 * @param {any} binary 图片二进制
 * @param {string} type 图片类型
 * @returns blob路径
 * @author YangZY
 * @date 20250509
 */
export function pictureClip(binary, type) {
  return new Promise((resolve, reject) => {
    const blob = new Blob([binary], { type: type });
    const url = window.URL.createObjectURL(blob);

    // 渲染图片得到图片信息
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");
    const image = new Image();
    image.src = url;

    image.onload = function () {
      canvas.width = image.width;
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
      const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
      const { data, width, height } = imageData;

      let startX = width;
      let startY = height;
      let endX = 0;
      let endY = 0;
      // 像素遍历
      for (let col = 0; col < width; col++) {
        for (let row = 0; row < height; row++) {
          const pxStartIndex = (row * width + col) * 4;
          const pxData = {
            r: data[pxStartIndex],
            g: data[pxStartIndex + 1],
            b: data[pxStartIndex + 2],
            a: data[pxStartIndex + 3],
          };
          // 不透明就是有颜色
          const colorExist = pxData.a !== 0;
          if (colorExist) {
            startX = Math.min(col, startX);
            endX = Math.max(col, startX);
            startY = Math.min(row, startY);
            endY = Math.max(row, endY);
          }
        }
      }
      // 追加边距
      endX += 11;
      endY += 11;
      startX -= 10;
      startY -= 10;
      // 渲染新的图片
      const cropCanvas = document.createElement("canvas");
      const cropCtx = cropCanvas.getContext("2d");
      cropCanvas.width = endX - startX;
      cropCanvas.height = endY - startY;
      cropCtx.drawImage(
        image,
        startX,
        startY,
        cropCanvas.width,
        cropCanvas.height,
        0,
        0,
        cropCanvas.width,
        cropCanvas.height
      );
      // 渲染结束返回url
      resolve(cropCanvas.toDataURL());
    };
  });
}
