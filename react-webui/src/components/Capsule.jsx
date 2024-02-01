const Capsule = ({ capsuleText }) => {
  const capsuleStyleNormal =
    ' text-neutral-500 bg-white font-base text-5xl font-sans  p-10 rounded-3xl shadow-2xl select-none hover:shadow-lg  ease-in-out duration-500  select-none " style={{cursor: "pointer"}}';

  return <div className={capsuleStyleNormal}>{capsuleText}</div>;
};

export default Capsule;
