<template>
  <div
    id="container"
    @mousedown="onMouseDown"
    @mousemove="onMouseMove"
    @mouseup="onMouseUp"
  >
    {{ selectionBoxStyle }}
    <div
      v-for="(element, index) in elements"
      :key="index"
      class="element"
      :style="element.style"
    ></div>
    <div id="selection-box" :style="selectionBoxStyle"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isDragging: false,
      startX: 0,
      startY: 0,
      endX: 0,
      endY: 0,
      selectionBoxStyle: {
        position: "absolute",
        border: "2px dashed yellow",
        pointerEvents: "none",
        opacity: 1,
        left: 0,
        top: 0,
        width: 0,
        height: 0
      },
      elements: [
        {
          style: {
            top: "50px",
            left: "50px",
            width: "100px",
            height: "100px",
            backgroundColor: "lightblue",
            border: "1px solid #000"
          }
        },
        {
          style: {
            top: "150px",
            left: "150px",
            width: "100px",
            height: "100px",
            backgroundColor: "lightblue",
            border: "1px solid #000"
          }
        },
        {
          style: {
            top: "250px",
            left: "250px",
            width: "100px",
            height: "100px",
            backgroundColor: "lightblue",
            border: "1px solid #000"
          }
        }
      ]
    };
  },
  methods: {
    onMouseDown(e) {
      this.selectionBoxStyle.opacity = 1;
      this.isDragging = true;
      this.startX = e.clientX;
      this.startY = e.clientY;
      this.selectionBoxStyle.display = "block";
      this.selectionBoxStyle.left = `${this.startX}px`;
      this.selectionBoxStyle.top = `${this.startY}px`;
      this.selectionBoxStyle.width = "0px";
      this.selectionBoxStyle.height = "0px";
	  

    },
    onMouseMove(e) {
      if (!this.isDragging) return;

      this.endX = e.clientX;
      this.endY = e.clientY;

      const x = Math.min(this.startX, this.endX);
      const y = Math.min(this.startY, this.endY);
      const width = Math.abs(this.endX - this.startX);
      const height = Math.abs(this.endY - this.startY);
      console.log(width, height);

      this.$nextTick(() => {
        this.selectionBoxStyle.left = `${x}px`;
        this.selectionBoxStyle.top = `${y}px`;
        this.selectionBoxStyle.width = `${width}px`;
        this.selectionBoxStyle.height = `${height}px`;
      });
    },
    onMouseUp() {
      this.isDragging = false;
      this.selectionBoxStyle.opacity = 0;

      const selectedElements = this.elements.filter(element => {
        const elementRect = this.getElementRect(element);
        const selectionRect = this.getSelectionBoxRect();

        return (
          elementRect.left >= selectionRect.left &&
          elementRect.right <= selectionRect.right &&
          elementRect.top >= selectionRect.top &&
          elementRect.bottom <= selectionRect.bottom
        );
      });

      console.log("Selected elements:", selectedElements);
    },
    getElementRect(element) {
      const el = document.createElement("div");
      el.style.position = "absolute";
      el.style.top = element.style.top;
      el.style.left = element.style.left;
      el.style.width = element.style.width;
      el.style.height = element.style.height;
      document.body.appendChild(el);
      const rect = el.getBoundingClientRect();
      document.body.removeChild(el);
      return rect;
    },
    getSelectionBoxRect() {
      const box = document.createElement("div");
      box.style.position = "absolute";
      box.style.left = this.selectionBoxStyle.left;
      box.style.top = this.selectionBoxStyle.top;
      box.style.width = this.selectionBoxStyle.width;
      box.style.height = this.selectionBoxStyle.height;
      document.body.appendChild(box);
      const rect = box.getBoundingClientRect();
      document.body.removeChild(box);
      return rect;
    }
  }
};
</script>

<style scoped>
#container {
  position: relative;
  width: 100%;
  height: 600px;
  border: 1px solid #ccc;
}

.element {
  position: absolute;
  width: 100px;
  height: 100px;
  background-color: lightblue;
  border: 1px solid #000;
}

#selection-box {
  position: absolute;
  border: 2px dashed red;
  pointer-events: none;
}
</style>
